import subprocess
import os

for filename in os.listdir("."):
    if not filename.endswith(".csv"):
        continue
    if not filename.startswith("internal"):
        continue
    for directory in ["customer1million", "customer5million", "customer10million", "customer15million"]:
        command = "python3 /home/dbconsent/code/choice-insertion/insertchoice.py {} {} {}"
        path = "./" + directory + "/" + filename.split("_")[0] + filename.split("_")[2].split(".")[0]
        modified = command.format("./" + filename, "./" + directory + "/", path)
        print(modified)
        process = subprocess.Popen(modified.split(), stdout=subprocess.PIPE)
        output, error = process.communicate()

