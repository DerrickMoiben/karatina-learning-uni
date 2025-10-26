maze = [
    [0, 0, 1, 0],
    [1, 0, 1, 0],
    [0, 0, 0, 0],
    [0, 1, 1, 0]
]

visited = []
path = []

def dfs(x, y):
    if (x, y) == (3, 3):
        path.append((x, y))
        return True

    if x < 0 or y < 0 or x >= 4 or y >= 4 or maze[x][y] == 1 or (x, y) in visited:
        return False

    visited.append((x, y))
    path.append((x, y))

    if dfs(x+1, y) or dfs(x, y+1) or dfs(x-1, y) or dfs(x, y-1):
        return True

    path.pop()
    return False

dfs(0, 0)
print("Visited Nodes:", visited)
print("Final Path:", path)
