
nginx + sidecar(topz)
```
APP_ID=$(docker run -d -p 80:80 nginx:alpine)
docker run --pid container:${APP_ID} -p 8080:8080 brendanburns/topz:db0fa58 /server -addr 0.0.0.0:8080
```

```
$ curl localhost:8080/topz
1 0 0.110427156 nginx: master process nginx -g daemon off;
7 0 0.04531867  nginx: worker process
8 0 0.04531867  nginx: worker process
9 0 0.09815747  /server -addr 0.0.0.0:8080
```

それぞれinspect時のPIDは違う。

     17             "Pid": 3677,

     17             "Pid": 3822,

よって、おそらく--pidはホスト側のPIDを共有する設定

