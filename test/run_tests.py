#!/usr/bin/env python3

import os
import subprocess

FILE = "../calculator.scala"


def make_test(test_name):
    with open(f"{test_name}.in", "r") as input_file:
        args = input_file.readline()
        output = subprocess.run(
            ["scala", FILE, "--"] + args.strip().split(), capture_output=True, text=True)
    with open(f"{test_name}.out", "r") as expected_file:
        return output.stdout == expected_file.readline(), output.stdout, output.returncode


test_list = filter(lambda x: x.endswith(".in"), os.listdir())

for test in test_list:
    test_name = test[:-3]
    if (os.path.isfile(f"{test_name}.in") and os.path.isfile(f"{test_name}.out")):
        result = make_test(test_name)
        if (result[0] and not result[2] != 0):
            print(f"test {test_name} is PASSED")
        else:
            print(
                f"test {test_name} is FAILED, returncode={result[2]}")
            print(result[1])
    else:
        print(f"in or out file for {test_name} is not found")
