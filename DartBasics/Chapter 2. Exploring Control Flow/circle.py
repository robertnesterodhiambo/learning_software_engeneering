import math
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import numpy as np

# ============================
# INFINITE PI DIGIT GENERATOR
# ============================
# Spigot algorithm for π (base-10)
def pi_digit_generator():
    q, r, t, k, n, l = 1, 0, 1, 1, 3, 3
    while True:
        if 4*q + r - t < n * t:
            yield n
            q, r, t, k, n, l = (
                10*q,
                10*(r - n*t),
                t,
                k,
                ((10*(3*q + r)) // t) - 10*n,
                l
            )
        else:
            q, r, t, k, n, l = (
                q*k,
                (2*q + r) * l,
                t * l,
                k + 1,
                (q*(7*k + 2) + r*l) // (t*l),
                l + 2
            )

# Initialize generator
pi_gen = pi_digit_generator()
next(pi_gen)  # Skip integer part "3"

# ============================
# PLOT SETUP
# ============================
RADIUS = 50
fig, ax = plt.subplots(figsize=(6, 6))
ax.set_aspect("equal")
ax.set_xlim(-RADIUS-1, RADIUS+1)
ax.set_ylim(-RADIUS-1, RADIUS+1)
ax.set_title("Infinite π Digits Animated Around a Circle")
ax.axis("off")

line, = ax.plot([], [], lw=1.5, color="blue")
point, = ax.plot([], [], 'ro', markersize=6)

# Draw static circle
theta = np.linspace(0, 2*math.pi, 300)
ax.plot(RADIUS*np.cos(theta), RADIUS*np.sin(theta), color="gray")

# Lists that keep growing forever
xs, ys = [], []

# ============================
# UPDATE FUNCTION
# ============================
def update(frame):
    digit = next(pi_gen)        # get next π digit (0-9)
    angle = (digit / 10) * 2*math.pi

    x = RADIUS * math.cos(angle)
    y = RADIUS * math.sin(angle)

    xs.append(x)
    ys.append(y)

    # Draw full path + current point
    line.set_data(xs, ys)
    point.set_data([x], [y])

    return line, point

# ============================
# RUN ANIMATION UNTIL YOU STOP
# ============================
ani = animation.FuncAnimation(
    fig, update, interval=100, blit=True
)

plt.show()
