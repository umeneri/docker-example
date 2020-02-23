Exception in thread "main" java.lang.IllegalStateException: unable to read from standard input; is standard input open and a tty attached?

→  --batchでOK

ERROR: [2] bootstrap checks failed
[1]: initial heap size [65011712] not equal to maximum heap size [1033895936]; this can cause resize pauses and prevents mlockall from locking the entire heap

→ [Initial heap size [199229440] not equal to maximum heap size [3187671040]; this can cause resize pauses and prevents mlockall from locking the entire heap - Elasticsearch - Discuss the Elastic Stack](https://discuss.elastic.co/t/initial-heap-size-199229440-not-equal-to-maximum-heap-size-3187671040-this-can-cause-resize-pauses-and-prevents-mlockall-from-locking-the-entire-heap/142282/6)
単純にデフォルトのjvm.optionsで対処


[2]: the default discovery settings are unsuitable for production use; at least one of [discovery.seed_hosts, discovery.seed_providers, cluster.initial_master_nodes] must be configured

→ ESの設定不足
seed_providersをつけて、zenのhosts_providerはずす


