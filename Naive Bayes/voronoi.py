import numpy as np
import cv2 as cv


def voronoi(size, num_cells):
    blank_image = np.zeros((size, size, 3), np.uint8)
    points = np.random.randint(0, size, [num_cells, 2])
    x = points[:, 0]
    y = points[:, 1]
    while True:
        r = np.random.randint(0, 256, [num_cells])
        if len(set(r)) is num_cells:
            break
    while True:
        g = np.random.randint(0, 256, [num_cells])
        if len(set(g)) is num_cells:
            break
    while True:
        b = np.random.randint(0, 256, [num_cells])
        if len(set(b)) is num_cells:
            break
    colors = []
    for i in range(num_cells):
        colors.append([b[i], g[i], r[i]])
    for n in range(size):
        for m in range(size):
            minimum_distance = ((1 - size)**2 + (1 - size)**2)**.5
            # the most distance there be is along the ghotr XD
            j = -1
            for i in range(num_cells):
                current_distance = ((m - x[i])**2 + (n - y[i])**2)**.5
                if current_distance < minimum_distance:
                    minimum_distance = current_distance
                    j = i
            blank_image[m, n] = colors[j]
    print(x)
    print(y)
    for i in range(num_cells):
        blank_image[x[i], y[i]] = (0, 0, 0)
        blank_image[x[i]+1, y[i]+1] = (0, 0, 0)
        blank_image[x[i]+1, y[i]-1] = (0, 0, 0)
        blank_image[x[i]-1, y[i]+1] = (0, 0, 0)
        # blacking
        blank_image[x[i]-1, y[i]-1] = (0, 0, 0)
        blank_image[x[i]+1, y[i]] = (0, 0, 0)
        blank_image[x[i]-1, y[i]] = (0, 0, 0)
        blank_image[x[i], y[i]+1] = (0, 0, 0)
        blank_image[x[i], y[i]-1] = (0, 0, 0)
    return blank_image, points, colors


def main():
    image, points, colors = voronoi(600, 4)
    check_points = np.random.randint(0, 600, [10, 2])
    print("my starting points were \n", points)
    print("my check points are \n", check_points)
    for point in check_points:
        x, y = point
        i = 0
        for c in colors:
            # print(" c is ", c)
            # print(image[x, y])
            b, g, r = c
            # print(b)
            # print(g)
            # print(r)
            bb, gg, rr = image[x, y]
            # print(bb)
            # print(gg)
            # print(rr)
            if bb == b and gg == g and rr == r:
                # there is a bug if we are that point or in the distance of 1
                print(point, " is closest to point number ", i, " which is ", points[i])
                break
            i += 1
    cv.imshow("voronoi", image)
    cv.waitKey()


if __name__ == '__main__':
    main()
