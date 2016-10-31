#!/bin/bash

#
#                           ******************                           
#*****************************  compilador  ****************************************
#**                         ******************                                    **
#**                                                                               **
#**Descripcion : Script que compila un test en especifico					      **
#**              SE LE PROVEE EL NUMERO UNICAMENTE, luego compila y guarda un ejecutable     **
#**              en la carpeta out                                                **
#***********************************************************************************

comp="dist/Compiler.jar" #nombre del jar.. 

file_name="t$1*"
file_path="tests/Fase4-ASM/$file_name" 
echo "///////////////////////// COMPILANDO $file_name /////////////////////////"

java -jar $comp $file_path

asmgen="$file_name*.s"

cd out
gcc ../lib/lib.c ../lib/alert.s $asmgen -o t$1

echo " "
echo "Compilation done. Executable file in out/t$1"
