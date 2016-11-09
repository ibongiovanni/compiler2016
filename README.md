#C-TDS Compiler 2016
---
La documentación se encuentra en la carpeta `doc`.

##Instrucciones:
   - Compilar 
   
   ```
   ant
   
   ```

Luego de que se genero el analizador léxico y sintáctico , compila y arma un JAR, llamado "Compiler.jar", dentro de la carpeta  "dist/".

   - Para compilar, por ejemplo un archivo "test.ctds" junto con una libreria "mylib.c" :
   ```
    ./compiler.sh ruta/hasta/test.ctds ruta/hasta/mylib.c
   
   ```
   
    En la carpeta `out` se habrá generado un archivo ejecutable `test`  
