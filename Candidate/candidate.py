import csv
import networkx as nx
from matplotlib.patches import Rectangle
import numpy as np
import matplotlib.pyplot as plt
import matplotlib


class Node(object):
    def __init__(self, h, level=0, parents=None):
        self.h = h
        self.level = level
        if parents is None:
            parents = []
        self.parents = set(parents)

    def __repr__(self):
        return "Node({}, {})".format(self.h, self.level)


def version_space(G, S):
    levels = [[Node(x, 0) for x in G]]
    print(levels)
    current = 1

    def next_level(h, S):
        for s in S:
            for i in range(len(h)):
                if h[i] == '?' and s[i] != '?':
                    yield h[:i] + (s[i],) + h[i + 1:]

    next = {}
    while True:
        for n in levels[-1]:
            for hypothesis in next_level(n.h, S):
                if hypothesis in next:
                    next[hypothesis].parents.add(n)
                else:
                    next[hypothesis] = Node(hypothesis, current, [n])
        if not next:
            break
        levels.append(list(next.values()))
        current += 1
        next = {}
    return levels


def g0(n):
    return ("?",)*n


def s0(n):
    return ("0",)*n


def genareler(hypo1, data):
    general = []
    for x, y in zip(hypo1, data):
        ans = x == "?" or (x != "0" and (x == y or y == "0"))
        general.append(ans)
    return all(general)


def minimumRequirementsOfGeneralization(hypo, x):
    new_hypothesis = list(hypo)
    for i in range(len(hypo)):
        if not genareler(hypo[i:i+1], x[i:i+1]):
            new_hypothesis[i] = "?" if hypo[i] != '0' else x[i]
    return [tuple(new_hypothesis)]


def minimumRequirementsOfSpecification(hypo, domains, x):
    lists = []
    for i in range(len(hypo)):
        if hypo[i] == "?":
            for val in domains[i]:
                if x[i] != val:
                    lists.append(hypo[:i]+(val,)+hypo[i+1:])
        elif hypo[i] != "0":
            lists.append(hypo[:i]+("0",)+hypo[i+1:])
    return lists


print(minimumRequirementsOfGeneralization(hypo=('0', '0'  , 'sunny'),
                    x=('rainy', 'windy', 'cloudy')))

print(minimumRequirementsOfSpecification(hypo=('?', 'x',),
                    domains=[['a', 'b', 'c'], ['x', 'y']],
                    x=('b', 'x')))


def get_domains(data):
    d = [set() for i in data[0]]
    for x in data[1:]:
        for i, xi in enumerate(x):
            d[i].add(xi)
    return [list(sorted(x)) for x in d]


def candidate_elimination(data):
    domains = get_domains(data)[:-1]
    G = set([g0(len(domains))])
    S = set([s0(len(domains))])
    glist = []
    slist = []
    glist.append(G)
    slist.append(S)
    i = 0
    print("\n G[{0}]:".format(i), G)
    print("\n S[{0}]:".format(i), S)
    for xcx in data[1:]:
        print(xcx)
        i = i + 1
        x, cx = xcx[:-1], xcx[-1]
        print(x)
        print(cx)
        if cx == 'Enjoy Sport':
            print("positive")
            G = {g for g in G if genareler(g, x)}
            S = generalize_S(x, G, S)
        else:
            print("negative")
            for s in S:
                print(s)
                print(genareler(s, x))
            print("before")
            print(S)
            S = {s for s in S if not genareler(s, x)}
            print(S)
            print("after")
            G = specialize_G(x, domains, G, S)
        glist.append(G)
        slist.append(S)
        print("\n G[{0}]:".format(i), G)
        print("\n S[{0}]:".format(i), S)
    return glist, slist


def generalize_S(x, G, S):
    S_prev = list(S)
    for s in S_prev:
        if s not in S:
            continue
        if not genareler(s, x):
            S.remove(s)
            Splus = minimumRequirementsOfGeneralization(s, x)
            S.update([h for h in Splus if any([genareler(g, h)
                                               for g in G])])
            S.difference_update([h for h in S if
                                 any([genareler(h, h1)
                                      for h1 in S if h != h1])])
    return S


def specialize_G(x, domains, G, S):
    G_prev = list(G)
    print("specialize G")
    for g in G_prev:
        print(g)
        print(genareler(g, x))
        if g not in G:
            continue
        if genareler(g, x):
            G.remove(g)
            Gminus = minimumRequirementsOfSpecification(g, domains, x)
            print("gminus ", Gminus)
            G.update([h for h in Gminus if any([genareler(h, s)
                                                for s in S])])
            print(G)
            G.difference_update([h for h in G if
                                 any([genareler(g1, h)
                                      for g1 in G if h != g1])])
            print(G)
    return G


def show(G, S):
    levels = version_space(G, S)
    print(levels)
    max = -1;
    for nodes in levels:
        print(len(nodes))
        if max<len(nodes):
            max = len(nodes)
    print("***************"*max)
    for nodes in levels:
        m = int(len(nodes)*35/max*2) if len(nodes) != max else 0
        s = " "*m
        for n in nodes:
            s += str(n.h)+"  "*int(35/max)
        print(s)
    draw(levels)


def draw(levels, ymin=0.15, ymax=0.85):

    g = nx.Graph()

    for nodes in levels:
        for n in nodes:
            for p in n.parents:
                g.add_edge(n.h, p.h)

    pos = {}

    for nodes, y in [(levels[0], ymin), (levels[-1], ymax)]:
        xvals = np.linspace(0, 1, len(nodes))
        for x, n in zip(xvals, nodes):
            pos[n.h] = [x, y]

    pos = nx.layout.fruchterman_reingold_layout(g, pos=pos, fixed=pos.keys())

    nx.draw_networkx_edges(g, pos=pos, alpha=0.25)
    nx.draw_networkx_labels(g, pos=pos)

    plt.box(True)
    plt.xticks([])
    plt.yticks([])
    plt.xlim(-1, 2)
    matplotlib.pyplot.gcf().set_size_inches((10, 10))
    # plt.show()


def main(f):
    with open(f) as file:
        data = [tuple(line) for line in csv.reader(file)]
    print(get_domains(data))
    glist, slist = candidate_elimination(data)
    # print(glist)
    # print(slist)
    print(glist[len(glist)-1])
    print(slist[len(slist)-1])
    print()
    print("*********start showing*********")
    print()
    show(glist[len(glist)-1], slist[len(slist)-1])


if __name__ == '__main__':
    main("trainingDataCandElim.csv")
