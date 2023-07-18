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
