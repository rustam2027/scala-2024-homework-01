import os


test_files = os.listdir()
for i in range(20):
    file_name = f"test_{i}.in"
    if (file_name not in test_files):
        input_data = input("Input data: ")
        with open(file_name, "x") as file:
            file.write(input_data)

        output_data = input("Output data: ")
        with open(f"test_{i}.out", "x") as file:
            file.write(input_data)
