#!/usr/bin/env python
# coding: utf-8

# In[2]:


import numpy as np
import matplotlib.pyplot as plt


# In[3]:


def compute_accelerations(M1, M2, M3, mu1, mu2, mu3, L):
    g = 9.81 
    a3 = (M2 * g * (2 * M3 + mu2) - M3 * g) / (M3 - M2)
    a2 = a3 + g * mu2
    N1 = M2 * g
    T = M2 * (a2 + g * mu2)
    a1 = (M2 * (a2 + g * mu2) - M1 * g) / M1
    D = 0.5 * a1 * L**2 / (a2 + g * mu2)
    return a1, a2, a3, D


# In[4]:


def visualize_motion(M1, M2, M3, mu1, mu2, mu3, L, dt, duration):
    g = 9.81  
    t = 0
    x1 = 0
    x2 = L
    x3 = L
    v1 = 0
    v2 = 0
    v3 = 0
    times = []
    positions_M1 = []
    positions_M2 = []
    positions_M3 = []

    while t <= duration:
        a1, a2, a3, _ = compute_accelerations(M1, M2, M3, mu1, mu2, mu3, L)
        v1 += a1 * dt
        v2 += a2 * dt
        v3 += a3 * dt
        x1 += v1 * dt
        x2 += v2 * dt
        x3 += v3 * dt
        times.append(t)
        positions_M1.append(x1)
        positions_M2.append(x2)
        positions_M3.append(x3)
        t += dt
    plt.plot(times, positions_M1, label='M1')
    plt.plot(times, positions_M2, label='M2')
    plt.plot(times, positions_M3, label='M3')
    plt.xlabel('Time (s)')
    plt.ylabel('Position (m)')
    plt.title('Motion of M1, M2, and M3')
    plt.legend()
    plt.grid(True)
    plt.show()


# In[5]:


M1 = 20
M2 = 7
M3 = 17
mu1 = 0.1
mu2 = 0.2
mu3 = 0.3
L = 11
dt = 0.001
duration = 10
a1, a2, a3, D = compute_accelerations(M1, M2, M3, mu1, mu2, mu3, L)
print("accelerations:")
print("a1:", a1)
print("a2:", a2)
print("a3:", a3)
print("maximal distance of M1", D)
visualize_motion(M1, M2, M3, mu1, mu2, mu3, L, dt, duration)


# In[6]:


M1 = 10
M2 = 24
M3 = 7
mu1 = 0.7
mu2 = 0.8
mu3 = 0.9
L = 110
dt = 0.001
duration = 10
a1, a2, a3, D = compute_accelerations(M1, M2, M3, mu1, mu2, mu3, L)
print("accelerations:")
print("a1:", a1)
print("a2:", a2)
print("a3:", a3)
print("maximal distance of M1", D)
visualize_motion(M1, M2, M3, mu1, mu2, mu3, L, dt, duration)


# In[7]:


M1 = 50
M2 = 25
M3 = 100
mu1 = 0.1
mu2 = 0.9
mu3 = 0.6
L = 4
dt = 0.001
duration = 10
a1, a2, a3, D = compute_accelerations(M1, M2, M3, mu1, mu2, mu3, L)
print("accelerations:")
print("a1:", a1)
print("a2:", a2)
print("a3:", a3)
print("maximal distance of M1", D)
visualize_motion(M1, M2, M3, mu1, mu2, mu3, L, dt, duration)


# In[8]:


M1 = 100
M2 = 200
M3 = 10
mu1 = 0.1
mu2 = 0.7
mu3 = 0.2
L = 12
dt = 0.001
duration = 10
a1, a2, a3, D = compute_accelerations(M1, M2, M3, mu1, mu2, mu3, L)
print("accelerations:")
print("a1:", a1)
print("a2:", a2)
print("a3:", a3)
print("maximal distance of M1", D)
visualize_motion(M1, M2, M3, mu1, mu2, mu3, L, dt, duration)


# **Summarizing report**
# higher M1: When M1 is higher compared to M2 and M3, it tends to move slower compared to the other bodies due to its high inertia.
# **Higher M2 or M3:** On the other hand, increasing the mass of M2 or M3 relative to M1 can lead to more noticeable motion of the pulley system, especially if the difference in mass between M2 and M3 is significant.
# Friction Coefficients (mu1, mu2, mu3):
# **High Friction:** Increasing the friction coefficients results in a decrease in acceleration for the respective bodies, affecting the overall dynamics of the system. High friction can also lead to a decrease in the maximal distance that M1 moves because of the increased resistance force.
# **Rope Length:**
# Shorter Rope: A shorter rope length would mean less distance for the pulley system to traverse. This could result in faster acceleration and higher velocities, especially for M1.
# Longer Rope: Similarly but on the other hand, a longer rope would require more time for the pulley system to traverse, potentially leading to slower acceleration and lower maximal speed for M1.
# **Summary:** Adjusting the masses and friction coefficients can lead to interesting effects. For instance, increasing the mass of M1 while decreasing the friction coefficient of M2's surface could result in M1 accelerating faster than M2.Or even finding the optimal rope length considering the masses and friction coefficients can result in maximizing the system's efficiency and achieving desired speeds for M1.

# In[ ]:




