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

////

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    YourRequestPOJO pojo = new YourRequestPOJO();
                    // Extract the file part from the form data
                    MultipartFile file = formData.getFirst("file");
                    if (file != null) {
                        // Set the file field in your POJO
                        pojo.setFile(file);
                    }

                    // You can also extract other fields from the form data and populate them in your POJO
                    // For example, if 'name' is another field in your POJO:
                    String name = formData.getFirst("name");
                    pojo.setName(name);

                    // Pass the modified POJO to the next filter in the chain
                    exchange.getAttributes().put("pojo", pojo);
                    return chain.filter(exchange);
                });
    }

In this example, we assume you are using Spring WebFlux framework. The exchange.getFormData() method returns a Mono<MultiValueMap<String, String>>, which represents the form data submitted with the request. You can use formData.getFirst("fieldName") to retrieve the file part and other fields by specifying their respective field names.

Make sure to replace YourRequestPOJO with the actual class name representing your Request POJO, and update the field names and extraction logic according to your specific requirements.

By including this custom filter in your application's filter chain, you can retrieve the MultipartFile and other fields from the request body and pass the modified POJO to subsequent filters or controllers in your application.




////

    @Bean
    public MultipartHttpMessageReader multipartHttpMessageReader() {
        MultipartHttpMessageReader reader = new MultipartHttpMessageReader();
        reader.setMultipartResolver(new StandardServletMultipartResolver());
        return reader;
    }








