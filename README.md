# uploadfile1

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    YourPOJO pojo = formData.toSingleValueMap().get("yourPOJOKey");
                    if (pojo != null) {
                        MultipartFile file = pojo.getMultipartFile();
                        // Pass the MultipartFile to the next filter or controller for further processing
                        exchange.getAttributes().put("file", file);
                    }
                    return chain.filter(exchange);
                });
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    YourPOJO pojo = new YourPOJO();
                    // Extract other fields from formData into the POJO

                    // Retrieve the MultipartFile from the request body
                    FilePart filePart = formData.getFirst("file"); // Assuming "file" is the name of the multipart file field

                    // Set the MultipartFile in the POJO
                    pojo.setFile(filePart != null ? filePart.content() : null);

                    // Pass the MultipartFile or the POJO to the next filter in the chain or use it as needed
                    exchange.getAttributes().put("pojo", pojo);
                    return chain.filter(exchange);
                });

                ////
                    private MultipartHttpMessageReader multipartReader;

    public MultipartFormFilter(MultipartHttpMessageReader multipartReader) {
        this.multipartReader = multipartReader;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getRequest().getBody()
                .collectList()
                .flatMap(dataBuffers -> {
                    byte[] byteArray = DataBufferUtils.join(dataBuffers).block().asByteBuffer().array();

                    FilePart filePart = new FilePart("file", "filename", byteArray);
                    YourPOJO pojo = new YourPOJO();
                    pojo.setFile(filePart);

                    exchange.getAttributes().put("pojo", pojo);
                    return chain.filter(exchange);
                });
    }
}
In this example, you would need to inject the MultipartHttpMessageReader into the MultipartFormFilter class during instantiation.

Replace YourPOJO with the actual class name representing your POJO, and adjust the logic for converting the Flux<DataBuffer> into a byte[] or DataBuffer based on your specific requirements.

By including this custom filter in your application's filter chain, you can retrieve the MultipartFile from the request body and populate it in the corresponding field of your POJO.








