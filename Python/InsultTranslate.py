"""
Written by Miggy :)
only issues:
    if you add another / or - within a translate or language (like / or -) then it types it and no error
    handlers for there for me to do :) (like hello - world - )
how to use:
    press '/' and write any text inside of it and end it with '\' to translate everything to spanish (defualt)
    press '-' and write any text inside of it and end it with '-' to change the language
    https://py-googletrans.readthedocs.io/en/latest/ for more info about languages (es for spanish, ja for japanese)
"""
from pynput import keyboard
import keyboard as kb
from googletrans import Translator


def delete_message():
    kb.press_and_release('ctrl+a')
    kb.press_and_release('backspace')


def translate(string, lang):
    translator = Translator()
    delete_message()
    try:
        translated = translator.translate(string, lang)
    except:
        translated = translator.translate(string, 'en')
    kb.write(translated.text)


def on_press(key):
    global lang_flag
    global flag
    global string
    global lang
    print(lang_flag , "-" ,flag)
    if key == keyboard.Key.esc:
        return False  # stop listener
    try:
        k = key.char  # single-char key
    except:
        k = key.name  # other keys
        if k == 'space':
            k = ' '
        else:
            k = ''
    if not lang_flag and not flag and k == '/': #for translate
        flag = True
        lang_flag = False
    elif not lang_flag and not flag and k == '-': #changing language
        lang_flag = True
        flag = False
        lang = ''
    elif flag and k != '\\': #read translate
        string += k
    elif flag and k == '\\': # ending translate
        translate(string, lang)
        string = ''
        flag = False
    elif lang_flag and k != '-': #read language
        lang += k
    elif lang_flag and k == '-': #ending language
        lang_flag = False
        delete_message()


flag = False
lang_flag = False
string = ''
lang = 'es'
listener = keyboard.Listener(on_press=on_press)
listener.start()
listener.join()