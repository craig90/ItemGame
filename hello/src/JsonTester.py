import json

class User:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def to_dict(self):
        return {"name": self.name, "age": self.age}
    
    def __repr__(self):
        return f"User(name'{self.name}', age={self.age})"
    
    def __eq__(self, other):
        if isinstance(other, User):
            return self.name == other.name and self.age == other.age


def addUniqueUser(users: list, name: str, age: int) -> bool:
    newUser = User(name,age)

    if newUser in users:
        print("Duplicate")
        return False
    else:
        print("New User")
        users.append(newUser)
        return True


with open("data/db.json", "r") as f:
    userDicts = json.load(f)

users = [User(user["name"], user["age"]) for user in userDicts]

print("Before: ", users)

if addUniqueUser(users, "Alice", 41) == True:
    with open("data/db.json", "w") as f:
        json.dump([user.to_dict() for user in users], f, indent=4)

    print("After:", users)



# newUser = User("Alice", 41)
# users.append(newUser)





# original user data
# users = []
# users.append(User("Craig", 35))
# users.append(User("Bob", 65))
# users.append(User("Meg", 35))
    