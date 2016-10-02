#C-TDS Compiler 2016
---
La documentación se encuentra en la carpeta `doc`.

##Instrucciones:
   - Compilar 

   >ant

Luego de que se genero el analizador léxico y sintáctico , compila y arma un JAR, llamado "Compiler.jar", dentro de la carpeta  "dist/".

   - Para ejecutar, por ejemplo los tests y mostrar la salida por consola:

   > ./runSyntaxTests.sh
   
   > ./runSemanticTests.sh
   
   ---
   - O para ejecutar los tests y guardar la salida en un archivo:
   
   > ./runSyntaxTests.sh > syntax-tests-results.txt 2>&1
   
   > ./runSemanticTests.sh > semantic-tests-results.txt 2>&1
