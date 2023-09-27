from petitparser import SettableParser
from petitparser import character as c

term = SettableParser.undefined()
prod = SettableParser.undefined()
prim = SettableParser.undefined()

number = c.digit().plus().flatten().trim().map(int)

term.set(
    (prod & c.of('+').trim() & term).map(lambda x: x[0] + x[2])
    | prod
)

prod.set(
    (prim & c.of('*').trim() & prod).map(lambda x: x[0] * x[2])
    | prim
)

prim.set(
    (c.of('(').trim() & term & c.of(')').trim())
        .map(lambda x: x[1])
    | number
)

start = term.end()

print(start.parse('1 + 2 * 3').value)   # 7
print(start.parse('(1 + 2) * 3').value) # 9