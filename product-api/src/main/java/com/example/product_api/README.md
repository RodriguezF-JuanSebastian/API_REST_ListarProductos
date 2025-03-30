1. Pruebas Unitarias con Mockito
Ubicadas en: src/test/java/com/example/product_api/ProductoServiceTest.java
¿Para qué sirven?
Las pruebas unitarias verifican el funcionamiento interno de ProductoService, asegurando que cada método se comporte correctamente de forma aislada.
Cómo ejecutarlas
Desde la terminal, dentro del proyecto, usa: mvn test
Métodos probados
listarProductos(): Verifica que se obtenga la lista de productos.
obtenerProductoPorId(): Comprueba que se devuelva el producto correcto por su ID.
crearProducto(): Confirma que un producto se guarda correctamente.

2. Pruebas de Integración con WebTestClient
Ubicadas en: src/test/java/com/example/product_api/ProductoIntegrationTest.java
¿Para qué sirven?
Simulan peticiones HTTP reales a los endpoints de la API, validando que el flujo completo funcione correctamente
Cómo ejecutarlas
Ejecuta todas las pruebas con: mvn test // O ejecuta solo las pruebas de integración con: mvn -Dtest=ProductoIntegrationTest test
Endpoints probados
POST /api/productos → Crea un nuevo producto y verifica que se guarde correctamente.
GET /api/productos/{id} → Obtiene un producto específico y valida su contenido.
DELETE /api/productos/{id} → Elimina un producto y confirma que ya no existe.

3. Pruebas Automatizadas en CI/CD (GitHub Actions)
Cuando subes cambios (git push), las pruebas se ejecutan automáticamente en GitHub Actions.
Cómo verificar en GitHub
Ve a al repositorio en GitHub.
Haz clic en la pestaña Actions.
Busca la ejecución del pipeline y revisa si las pruebas pasaron o fallaron.