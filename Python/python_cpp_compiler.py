import tkinter as tk
from tkinter import filedialog, messagebox
import subprocess

success = True

def assemble_and_link(file_name):
    global success
    update_infobox("\nAttempting to assemble and link the process....", False)
    try:
        subprocess.run(["gcc", "-c", (file_name + ".S"), "-o", (file_name + ".obj")], check=True)
        update_infobox(f"\nObject {file_name}.obj created successfully.", False)

        subprocess.run(["g++", (file_name + ".obj"), "-o", file_name], check=True)
        update_infobox(f"\nExecutable file {file_name}.exe created successfully.", False)
        success = True

    except subprocess.CalledProcessError as e:
        update_infobox(f"Error during assembly/linking: {e}")
        success = False

def cpp_to_asm(file_name, path):
    global success

    # to append for raw cpp
    PREFIX = "#include <iostream>\nusing namespace std;\nint main() {\n"
    SUFFIX = "\ncout<<endl;\nsystem(\"pause\");\nreturn 0;\n}\n"

    # opens the given cpp file to read and moves them to "text_append" to be appended on raw cpp (don't ask me bat ganito ginawa ko)
    f = open(path, 'r', encoding='utf8')
    text_append = ""
    with f as file:
        for line in file:
            text_append += line

    # rewrites them into raw.cpp
    f = open("raw.cpp", 'w', encoding='utf8')
    f.write(PREFIX)
    f.write(text_append)
    f.write(SUFFIX)
    f.close()

    #compiling
    compile_command = ["g++", "-S", "raw.cpp", "-o", (file_name + ".S")]
    result = subprocess.run(compile_command, capture_output=True, text=True)
    if result.returncode == 0:
        update_infobox(f"Compilation successful! created at: {file_name}.S")
        assemble_and_link(file_name)
    else:
        update_infobox(f"Compilation failed! Error: {result.stderr}")
        success = False

def converter(path, output):
    cpp_to_asm(output, path)

def update_infobox(msg, is_delete=True):
    info_text_box.configure(state='normal')
    if is_delete:
        info_text_box.delete(1.0, tk.END)
    info_text_box.insert(tk.END, msg)
    info_text_box.configure(state='disabled')

def import_file():
    file_path = filedialog.askopenfilename(
        initialdir=".",  # starts at the current dir, not documents
        filetypes=[("C++ files", "*.cpp")],
        title="Select a C++ file"
    )
    if file_path:
        file_name = file_path
        file_path_display.configure(state='normal')
        file_path_display.delete(0, tk.END)
        file_path_display.insert(0, file_name)
        file_path_display.configure(state='disabled')

def generate_action():
    if file_path_display.get():
        path = file_path_display.get()
        output = file_name_entry.get()
        converter(path,output)
        if success:
            messagebox.showinfo('Info', "Successfully generated!")
        else:
            messagebox.showinfo('Error', "Failed to generate, check the error log!")
    else:
        update_infobox("No File imported, try again!")

def show_info():
    messagebox.showinfo("Info", "Written by:\n\n Miggy")

root = tk.Tk()
root.title("CSS125L - Final Project")

root.resizable(False, False)

menu_bar = tk.Menu(root)
root.config(menu=menu_bar)

info_menu = tk.Menu(menu_bar, tearoff=0)
menu_bar.add_cascade(label="Info", menu=info_menu)
info_menu.add_command(label="About", command=show_info)

# file path display lang, not editable
file_path_display = tk.Entry(root, width=50, state='readonly')
file_path_display.grid(row=0, column=0, padx=10, pady=10)

import_button = tk.Button(root, text="Import File", command=import_file)
import_button.grid(row=0, column=1, padx=10, pady=10)

# file name
file_name_entry = tk.Entry(root, width=50)
file_name_entry.grid(row=1, column=0, padx=10, pady=10)

file_name_label = tk.Label(root, text="File Name")
file_name_label.grid(row=1, column=1, padx=10, pady=10, sticky="w")

# diplaying stuffs
info_text_box = tk.Text(root, width=60, height=5, state='disabled')
info_text_box.grid(row=2, column=0, padx=10, pady=10, columnspan=2)

generate_button = tk.Button(root, text="Generate", command=generate_action)
generate_button.grid(row=3, column=0, padx=10, pady=10, columnspan=2)

root.mainloop()
