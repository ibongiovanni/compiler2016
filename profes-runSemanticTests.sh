#!/bin/bash

#
#                           ******************                           
#*****************************     test     ****************************************
#**                         ******************                                    **
#**                                                                               **
#**Descripcion : Script que corre los casos de prueba definidos en la carpeta     **
#**              tests. cada test tiene su propio main.                          **
#**                                                                               **
#***********************************************************************************   
echo "*********************************************************************************** "
echo "**                                                                               ** " 
echo "**                            Ejecutando casos de prueba . . .                    ** "                
echo "**                                                                               ** "
echo "**                                                                               ** "
echo "*********************************************************************************** "
echo "     " 
echo "     " 

comp="dist/Compiler.jar" #nombre del jar.. 


echo "///////////////////////// TESTS CORRECTOS ////////////////////////////////////////" 
files=`ls tests/tests_semanticos/test_correctos/*.ctds`


for file in $files ; do 
	echo "---------------- Test $file ... -------------------" 
        java -jar $comp $file 
	echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

echo "///////////////////////// TESTS CON FALLAS ////////////////////////////////////////" 

files=`ls tests/tests_semanticos/test_errores/*.ctds`

for file in $files ; do 
	echo "---------------- Test $file ... -------------------" 
        java -jar $comp $file  
	echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

exit 0

