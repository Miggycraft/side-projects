from sympy import symbols, Eq, solve
# A EQUATION
a = float(input("Enter 4th Exponent [A]:"))
b = float(input("Enter 3rd Exponent [B]:"))
c = float(input("Enter 2nd Exponent [C]:"))
d = float(input("Enter 1st Exponent [D]:"))
e = float(input("Enter Constant [E]:"))

subscript = "\u2080"

guess_r = float(input(f"Initial Guess r{subscript}:"))
guess_s = float(input(f"Initial Guess s{subscript}:"))
r, s = symbols('r s')
rel_err1 = 100
rel_err2 = 100
i = 0

def incSub():
    global subscript
    subscript = (chr(ord(subscript) + 1))


def errIsValid(err1, err2, threshold):
    if err1 > threshold or err2 > threshold:
        return True
    return False

while errIsValid(rel_err1, rel_err2, 1):
    print(f'ITERATION[{i+1}]:')
    i += 1

    # B EQUATION
    b_a = a
    b_b = b + guess_r * b_a
    b_c = c + guess_r * b_b + guess_s * b_a
    b_d = d + guess_r * b_c + guess_s * b_b
    b_e = e + guess_r * b_d + guess_s * b_c

    # C EQUATION
    c_a = a
    c_b = b_b + guess_r * c_a
    c_c = b_c + guess_r * c_b + guess_s * c_a
    c_d = b_d + guess_r * c_c + guess_s * c_b
    c_e = b_e + guess_r * c_d + guess_s * c_c

    one = -b_d
    two = -b_e
    eq1 = Eq(c_c * r + c_b * s, one)
    eq2 = Eq(c_d * r + c_c * s, two)
    sol_dict = solve((eq1, eq2), (r, s))

    root1 = (guess_r + sol_dict[r])
    root2 = (guess_s + sol_dict[s])

    rel_err1 = (abs((sol_dict[r])/root1) * 100)
    rel_err2 = (abs((sol_dict[s])/root2) * 100)

    incSub()
    print(f"r{subscript}: {root1.round(3)}")
    print(f"s{subscript}: {root2.round(3)}")
    print(f"%ERR r{subscript}: {rel_err1.round(2)}%")
    print(f"%ERR s{subscript}: {rel_err2.round(2)}%")

    guess_r = root1
    guess_s = root2
    print("\n---------------------")
