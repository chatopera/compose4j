# Compose4J

Onion Model in Java

![Onion Model in Java](https://user-images.githubusercontent.com/3538629/67161491-a115a780-f38d-11e9-9155-69e01397856c.png)

## Philosophy

> Composer->Middle.apply(Context)

## Required

* Maven
* Java 8+

## Usage

```
        Composer composer = new Composer<SampleContext>();
        composer.use(new Mw1());
        composer.use(new Mw2());

        SampleContext context = new SampleContext();

        composer.handle(context);

        logger.info("[testCompose] result");

        print(context.getData());
```

Checkout this [sample codes](https://github.com/chatopera/composer4j/blob/master/src/test/java/com/chatopera/compose4j/Compose4jTest.java) for details.


## Contribute

```
cd compose4j
mvn test
```


# License

Copyright (2019) <a href="https://www.chatopera.com/" target="_blank">北京华夏春松科技有限公司</a>

[Apache License Version 2.0](https://github.com/chatopera/compose4j/blob/master/LICENSE)

[![chatoper banner][co-banner-image]][co-url]

[co-banner-image]: https://user-images.githubusercontent.com/3538629/42383104-da925942-8168-11e8-8195-868d5fcec170.png
[co-url]: https://www.chatopera.com
