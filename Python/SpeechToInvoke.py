import time
import speech_recognition as sr
import keyboard as kb


def combo(skill1, skill2): #for multiple skills
    kb.press_and_release('f')
    kb.press_and_release('d')
    time.sleep(0.2)
    useSkill(skill1)
    time.sleep(0.15)
    useSkill(skill2)
    kb.press_and_release('r')
    time.sleep(0.15)
    kb.press_and_release('f')
    time.sleep(0.3)
    kb.press_and_release('d')


def useSkill(skills): #to shorten in invokerCall function
    for skill in skills:
        kb.press_and_release(skill)
    kb.press_and_release('r')

    time.sleep(1.1) #to delay the next call (invoke has approx 1 sec cool down)


def invokerCall(word):  # detects if mga skills ni invo then mu keyboard press
    if word.__contains__("cold") or word.__contains__("snap") or word.__contains__("call") or word.__contains__(
            "stop"):
        useSkill('qqq')

    if word.__contains__("ghost") or word.__contains__("walk"):  # Ghostwalk
        useSkill('qqw')

    if word.__contains__("ice") or word.__contains__("wall"):  # Ice wall
        useSkill('qqe')

    if word.__contains__("mp") or word.__contains__("np"):  # EMP
        useSkill('www')

    if word.__contains__("nado") or word.__contains__("doh"):  # Tornado
        useSkill('wwq')

    if word.__contains__("tie") or word.__contains__("ity"):  # Alacrity
        useSkill('wwe')

    if word.__contains__("sun") or word.__contains__("strike"):  # sunstrike
        useSkill('eee')

    if word.__contains__("forge") or word.__contains__("irit") or word.__contains__("eet") or word.__contains__(
            "ford"):  # forge spirit
        useSkill('eeq')

    if word.__contains__("chaos") or word.count("meet") or word.__contains__("meteor"):  # chaos meteor
        useSkill('eew')

    if word.__contains__("deaf") or word.__contains__("last"):  # defeaning blast
        useSkill('qwe')

    if word.__contains__("yolo"):  # tornado, emp, invokes meteor and deafening
        combo('eew', 'qwe')


recognizer = sr.Recognizer()
mic = sr.Microphone(device_index=1)

print("SpeechToInvoker intialized!")
with mic as source:
    while True:
        captured_audio = recognizer.listen(source=mic, phrase_time_limit=1)
        # listens for x amount of time then invoke the skill
        try:
            word = recognizer.recognize_google(captured_audio)
            invokerCall(word)
        except:
            print("Couldn't Listen :(")
