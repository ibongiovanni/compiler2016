#!/bin/bash

#
#                           ********************************                           
#*****************************  compilador de codigo C-TDS  ************************
#**                         ********************************                      **
#**                                                                               **
#**Descripcion : Script que compila un archivo en lenguaje c-tds y genera un      **
#**              ejecutable en la carpeta out.     								  **
#** 																			  **
#**Syntaxis : ./compile.sh INPUT [librerias de usuario...]                        **
#**                                												  **
#**Ejemplo : ./compile.sh test.ctds libs/mylib.c libs/mylib2.s 					  **
#***********************************************************************************

comp="dist/Compiler.jar" #nombre del jar.. 

if (( $# > 0 )); then
	full_name_and_path="$1"
	#file_name=`sed "s/.*\///" $full_name_and_path`
	xpath="${full_name_and_path%/*}"
	echo "xpath: $xpath" 
	file_name="${full_name_and_path##*/}"
	echo "file_name: $file_name"
	file_name_no_ext="${file_name%.ctds}"
	echo "file_name_no_ext: $file_name_no_ext"

	#file_path="tests/Fase5-Opt/$file_name" 
	echo "///////////////////////// COMPILANDO $file_name /////////////////////////"

	java -jar $comp $full_name_and_path

	echo " "
	echo "///////////////////////// CODIGO ENSAMBLADOR GENERADO /////////////////////////"
	asmgen="$file_name_no_ext.s"
	libs="lib/alert.s"

	# User libraries
	userLibsCount=$(( $#-1 ))
	for (( c=1; c<=$userLibsCount; c++ ))
	do  
	   libs="$libs $2"
	   shift #shift moves the positional parameter values
	   echo "Added library: $1" # $1 is now what it used to be $2
	done
	echo "libraries: $libs"

	outdir="out/"
	#cd out
	gcc $libs $outdir/$asmgen -o $outdir/$file_name_no_ext

	echo " "
	echo "Compilation done. Executable file in out/$file_name_no_ext"
fi
