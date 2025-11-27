import math
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import numpy as np

# --- SETTINGS ---
NUM_DIGITS = 2000         # How many digits of π to animate
RADIUS = 50               # Circle radius
DRAW_SPEED = 300          # ms between frames

# Convert π to digits
pi_digits = str(math.pi).replace(".", "")[:NUM_DIGITS]

# Convert each digit (0–9) to an angle
angles = [(int(d) / 10) * 2 * math.pi for d in pi_digits]

# Pre-compute circle positions
xs = [RADIUS * math.cos(a) for a in angles]
ys = [RADIUS * math.sin(a) for a in angles]

# --- PLOT SETUP ---
fig, ax = plt.subplots(figsize=(6, 6))
ax.set_aspect("equal")
ax.set_xlim(-RADIUS - 1, RADIUS + 1)
ax.set_ylim(-RADIUS - 1, RADIUS + 1)
ax.set_title("Animated π Digits Around a Circle")
ax.axis("off")

line, = ax.plot([], [], lw=1.5, color="blue")
point, = ax.plot([], [], 'ro', markersize=6)

# Draw the circle outline
theta = np.linspace(0, 2 * math.pi, 300)
ax.plot(RADIUS * np.cos(theta), RADIUS * np.sin(theta), color="gray")

# --- ANIMATION FUNCTION ---
def update(frame):
    # Draw trajectory so far
    line.set_data(xs[:frame], ys[:frame])
    
    # Draw current point (as list, not scalar)
    point.set_data([xs[frame - 1]], [ys[frame - 1]])

    return line, point

ani = animation.FuncAnimation(
    fig,
    update,
    frames=len(xs),
    interval=DRAW_SPEED,
    blit=True
)

plt.show()
