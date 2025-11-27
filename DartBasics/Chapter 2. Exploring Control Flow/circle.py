import matplotlib.pyplot as plt
import numpy as np
from mpmath import mp

# --------------------------------------------
# Compute PI digits using mpmath
# --------------------------------------------
N = 500  # number of digits
mp.dps = N + 5  # precision

pi_str = str(mp.pi)
pi_digits = pi_str.replace(".", "")[1:N+1]
digits = [int(d) for d in pi_digits]

# --------------------------------------------
# Prepare circle layout (0–9 around circle)
# --------------------------------------------
angles = np.linspace(0, 2 * np.pi, 10, endpoint=False)
x = np.cos(angles)
y = np.sin(angles)
pos = {digit: (x[i], y[i]) for i, digit in enumerate(range(10))}

# --------------------------------------------
# Plot
# --------------------------------------------
plt.figure(figsize=(8, 8))

# Draw labeled circle points
for d in range(10):
    px, py = pos[d]
    plt.scatter(px, py, s=200)
    plt.text(px, py, str(d), fontsize=16, ha='center', va='center', color='white')

# Connect lines following π digits
for i in range(len(digits) - 1):
    d1, d2 = digits[i], digits[i + 1]
    x_vals = [pos[d1][0], pos[d2][0]]
    y_vals = [pos[d1][1], pos[d2][1]]
    plt.plot(x_vals, y_vals, alpha=0.3)

plt.title(f"Circle Visualization of First {N} Digits of π", fontsize=16)
plt.axis("off")
plt.show()
