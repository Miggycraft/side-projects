import tkinter as tk
from tkinter import filedialog, messagebox
import subprocess

success = True
# to append for raw cpp
PREFIX = "#include <iostream>\nusing namespace std;\nint main() {\n"
SUFFIX = "\ncout<<endl;\nsystem(\"pause\");\nreturn 0;\n}\n"

def converter(path, output="output"):
    global success
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

    # compiling
    exe_file = f"{output}.exe"
    compile_command = ["g++", "raw.cpp", "-o", exe_file, "-mconsole"]
    result = subprocess.run(compile_command, capture_output=True, text=True)
    if result.returncode == 0:
        update_infobox(f"Compilation successful! Executable created: {exe_file}")
        success = True
    else:
        update_infobox(f"Compilation failed! Error: {result.stderr}")
        success = False

def update_infobox(msg):
    info_text_box.configure(state='normal')
    info_text_box.delete(1.0, tk.END)
    info_text_box.insert(tk.END, msg)
    info_text_box.configure(state='disabled')

# Function to open file dialog and get the file name
def import_file():
    file_path = filedialog.askopenfilename(
        initialdir=".",  # Start in current directory
        filetypes=[("C++ files", "*.cpp")],  # Accept only .cpp files
        title="Select a C++ file"
    )
    if file_path:
        file_name = file_path
        file_path_display.configure(state='normal')
        file_path_display.delete(0, tk.END)
        file_path_display.insert(0, file_name)
        file_path_display.configure(state='disabled')

# Function for the "Generate" button (modify as needed)
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


# Function to display info message
def show_info():
    messagebox.showinfo("Info", "Written by:\n\n Miggy")

# Create main window
root = tk.Tk()
root.title("CSS125L - Final Project")

# Disable minimize/maximize buttons
root.resizable(False, False)

# Create menu bar
menu_bar = tk.Menu(root)
root.config(menu=menu_bar)

# Add Info menu
info_menu = tk.Menu(menu_bar, tearoff=0)
menu_bar.add_cascade(label="Info", menu=info_menu)
info_menu.add_command(label="About", command=show_info)

# Create the file entry field (uneditable)
file_path_display = tk.Entry(root, width=50, state='readonly')
file_path_display.grid(row=0, column=0, padx=10, pady=10)

# Create the Import button
import_button = tk.Button(root, text="Import File", command=import_file)
import_button.grid(row=0, column=1, padx=10, pady=10)

# Create label and text field for file name
file_name_entry = tk.Entry(root, width=50)
file_name_entry.grid(row=1, column=0, padx=10, pady=10)

file_name_label = tk.Label(root, text="File Name")
file_name_label.grid(row=1, column=1, padx=10, pady=10, sticky="w")

# Create a disabled Text box for displaying messages or information
info_text_box = tk.Text(root, width=60, height=5, state='disabled')
info_text_box.grid(row=2, column=0, padx=10, pady=10, columnspan=2)

# Create the Generate button
generate_button = tk.Button(root, text="Generate", command=generate_action)
generate_button.grid(row=3, column=0, padx=10, pady=10, columnspan=2)

# Run the application
root.mainloop()
