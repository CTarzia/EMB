package nl.knaw.huygens.timbuctoo.v5.redirectionservice.bitly;

import net.swisstech.bitly.BitlyClient;
import net.swisstech.bitly.model.Response;
import net.swisstech.bitly.model.v3.ShortenResponse;
import nl.knaw.huygens.persistence.PersistenceException;
import nl.knaw.huygens.timbuctoo.core.NotFoundException;
import nl.knaw.huygens.timbuctoo.core.TransactionState;
import nl.knaw.huygens.timbuctoo.core.dto.EntityLookup;
import nl.knaw.huygens.timbuctoo.util.Tuple;
import nl.knaw.huygens.timbuctoo.v5.dataset.AddTriplePatchRdfCreator;
import nl.knaw.huygens.timbuctoo.v5.dataset.DataSetRepository;
import nl.knaw.huygens.timbuctoo.v5.dataset.ImportManager;
import nl.knaw.huygens.timbuctoo.v5.dataset.dto.DataSet;
import nl.knaw.huygens.timbuctoo.v5.dataset.dto.DataSetMetaData;
import nl.knaw.huygens.timbuctoo.v5.filestorage.exceptions.LogStorageFailedException;
import nl.knaw.huygens.timbuctoo.v5.queue.QueueManager;
import nl.knaw.huygens.timbuctoo.v5.redirectionservice.RedirectionService;
import nl.knaw.huygens.timbuctoo.v5.redirectionservice.RedirectionServiceParameters;
import nl.knaw.huygens.timbuctoo.v5.redirectionservice.exceptions.RedirectionServiceException;
import nl.knaw.huygens.timbuctoo.v5.util.RdfConstants;
import org.slf4j.Logger;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class BitlyService extends RedirectionService {
  public static final String BITLY_QUEUE = "bitly";
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BitlyService.class);
  private static final String PERSISTENT_ID = RdfConstants.timPredicate("persistentUri");
  private final BitlyClient bitlyClient;
  private final DataSetRepository dataSetRepository;

  public BitlyService(QueueManager queueManager, DataSetRepository dataSetRepository,
                      String accessToken) {
    super(BITLY_QUEUE, queueManager);
    this.dataSetRepository = dataSetRepository;
    bitlyClient = new BitlyClient(accessToken);
  }

  @Override
  protected void oldSavePid(RedirectionServiceParameters params) {
    URI uri = params.getUrlToRedirectTo();
    String persistentUrl = retrieveBitlyUri(uri.toString());
    transactionEnforcer.execute(timbuctooActions -> {
        try {
          timbuctooActions.addPid(URI.create(persistentUrl), params.getEntityLookup());
          LOG.info("committed pid");
          return TransactionState.commit();
        } catch (NotFoundException e) {
          LOG.warn("Entity for entityLookup '{}' cannot be found", params.getEntityLookup());
          bitlyClient.userLinkEdit().setArchived(true);

          return TransactionState.rollback();
        }
      }
    );
  }

  @Override
  protected void savePid(RedirectionServiceParameters params) throws PersistenceException,
    RedirectionServiceException {
    URI uri = params.getUrlToRedirectTo();
    LOG.info(String.format("Retrieving persistent url for '%s'", uri));
    String persistentUrl = retrieveBitlyUri(uri.toString());

    EntityLookup entityLookup = params.getEntityLookup();

    String dataSetId = entityLookup.getDataSetId().get();

    Tuple<String, String> ownerIdDataSetId = DataSetMetaData.splitCombinedId(dataSetId);

    Optional<DataSet> maybeDataSet = dataSetRepository.getDataSet(
      entityLookup.getUser().get(),
      ownerIdDataSetId.getLeft(),
      ownerIdDataSetId.getRight());

    if (!maybeDataSet.isPresent()) {
      throw new PersistenceException("Can't retrieve DataSet");
    }

    DataSet dataSet = maybeDataSet.get();

    final ImportManager importManager = dataSet.getImportManager();

    try {
      importManager.generateLog(
          dataSet.getMetadata().getBaseUri(),
        dataSet.getMetadata().getGraph(),
        new AddTriplePatchRdfCreator(
          entityLookup.getUri().get(),
          PERSISTENT_ID,
          persistentUrl,
          RdfConstants.STRING)
      ).get();
    } catch (LogStorageFailedException | InterruptedException | ExecutionException e) {
      throw new RedirectionServiceException(e);
    }

  }

  public String retrieveBitlyUri(String uri) {
    Response<ShortenResponse> call = bitlyClient.shorten().setLongUrl(uri).call();

    if (call.status_code != 200) {
      return null;
    }
    return call.data.url;
  }
}
