#!/bin/bash

# Settings
path_to_compiler=/home/Joklost/git/P4-Code/battlesim-compiler/battlesim-compiler.jar


function assert {
	# $1 compile filename
	# $2 expected result filename
	# $3 test number
	java -jar $path_to_compiler	$1
	java -jar Main.jar > results_real
	EXPTRES=$(cat $2)
	REALRES=$(cat results_real)

	if [ "$EXPTRES" = "$REALRES" ]; then
		echo "Test $3: Success"	
	else	
		echo "Test $3: Fail"
	fi

	rm results_real Main.jar
}

function assertCompileError {
	# $1 compile filename
	# $2 expected result filename
	# $3 test number
	java -jar $path_to_compiler	$1 2> results_real
	EXPTRES=$(cat $2)
	REALRES=$(cat results_real)

	if [ "$EXPTRES" = "$REALRES" ]; then
		echo "Test $3: Success"	
	else	
		echo "Test $3: Fail"
	fi

	rm results_real
}

assert Test1_BubbleSort.bs Test1_results_expt 1 #!! Sorta broken/useless after d9f9320
assert Test2_Recursion.bs Test2_results_expt 2
assert Test3_ReturnInteger.bs Test3_results_expt 3
assert Test4_ReturnDecimal.bs Test4_results_expt 4
assert Test5_TestWhile.bs Test5_results_expt 5
assert Test6_TestArrayForeach.bs Test6_results_expt 6
assert Test7_TestMath.bs Test7_results_expt 7
assert Test8_TestBool.bs Test8_results_expt 8
assert Test9_TestIf.bs Test9_results_expt 9
assert Test10_TestSwitch.bs Test10_results_expt 10
assert Test11_TestFor.bs Test11_results_expt 11
assert Test12_TestScope.bs Test12_results_expt 12
assert Test13_CustomTypeParam.bs Test13_results_expt 13
