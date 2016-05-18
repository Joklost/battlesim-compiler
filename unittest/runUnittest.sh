#!/bin/bash

# Settings
path_to_compiler=/home/pgug/Code/P4-Code/battlesim-compiler/battlesim-compiler.jar
PRINTERROR=false

function assert {
	# $1 compile filename
	# $2 expected result filename
	if [ $PRINTERROR = true ]; then
		java -jar $path_to_compiler	$1
		java -jar Main.jar > results_real
	else
		java -jar $path_to_compiler	$1 > /dev/null 2> /dev/null
		java -jar Main.jar > results_real 2> /dev/null
	fi
	EXPTRES=$(cat $2)
	REALRES=$(cat results_real)

	if [ "$EXPTRES" = "$REALRES" ]; then
		echo "$1: Success"	
	else	
		echo "$1: Fail"
	fi

	if [ -f results_real ]; then
		rm results_real
	fi
	if [ -f Main.jar ]; then
		rm Main.jar
	fi
}

function assertCompileError {
	# $1 compile filename
	# $2 expected result filename
	if [ $PRINTERROR = true ]; then
		java -jar $path_to_compiler	$1 2> results_real
	else
		java -jar $path_to_compiler	$1 2> results_real > /dev/null
	fi
	EXPTRES=$(cat $2)
	REALRES=$(cat results_real)

	if [ "$EXPTRES" = "$REALRES" ]; then
		echo "Test $1: Success"	
	else
		echo "Test $1: Fail"
	fi

	if [ -f results_real ]; then
		rm results_real
	fi
}

assert Test1_BubbleSort.bs Test1_results_expt #!! Sorta broken/useless after d9f9320
assert Test2_Recursion.bs Test2_results_expt
assert Test3_ReturnInteger.bs Test3_results_expt
assert Test4_ReturnDecimal.bs Test4_results_expt
assert Test5_TestWhile.bs Test5_results_expt
assert Test6_TestArrayForeach.bs Test6_results_expt
assert Test7_TestMath.bs Test7_results_expt
assert Test8_TestBool.bs Test8_results_expt
assert Test9_TestIf.bs Test9_results_expt
assert Test10_TestSwitch.bs Test10_results_expt
assert Test11_TestFor.bs Test11_results_expt
assert Test12_TestScope.bs Test12_results_expt
assert Test13_CustomTypeParam.bs Test13_results_expt
assert Test14_TestTypes.bs Test14_results_expt
assert Test15_EngineStressTest.bs Test15_results_expt
