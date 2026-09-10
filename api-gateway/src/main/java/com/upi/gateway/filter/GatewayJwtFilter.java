//@Component
//public class GatewayJwtFilter implements GlobalFilter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        String path = exchange.getRequest().getURI().getPath();
//
//        if (path.contains("/auth")) {
//            return chain.filter(exchange);
//        }
//
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new RuntimeException("Missing token");
//        }
//
//        String token = authHeader.substring(7);
//        jwtUtil.extractUsername(token); // validate
//
//        return chain.filter(exchange);
//    }
//}