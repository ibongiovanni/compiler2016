#!/bin/bash

#
#                           ********************************                           
#*****************************  compilador de test fase 5   ************************
#**                         ********************************                      **
#**                                                                               **
#**Descripcion : Script que compila un test en especifico de la carpeta Fase5,    **
#**              SE LE PROVEE EL NUMERO UNICAMENTE, luego compila y genera un     **
#**              ejecutable en la carpeta out.                                    **
#***********************************************************************************

comp="dist/Compiler.jar" #nombre del jar.. 

if (( $# > 0 )); then
	file_name="t$1*"
	file_path="tests/Fase5-Opt/$file_name" 
	echo "///////////////////////// COMPILANDO $file_name /////////////////////////"

	java -jar $comp $file_path

	asmgen="$file_name*.s"

	cd out
	gcc ../lib/lib.c ../lib/alert.s $asmgen -o t5_$1

	echo " "
	echo "Compilation done. Executable file in out/t5_$1"
fi
