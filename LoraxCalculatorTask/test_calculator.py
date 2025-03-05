import pytest
from calculator import Calculator

@pytest.fixture
def calculator():
    return Calculator()

def test_add(calculator):
    assert calculator.add(3, 2) == 5
    assert calculator.add(-1, 1) == 0
    assert calculator.add(0, 0) == 0

def test_subtract(calculator):
    assert calculator.subtract(5, 3) == 2
    assert calculator.subtract(0, 5) == -5
    assert calculator.subtract(-3, -2) == -1

def test_multiply(calculator):
    assert calculator.multiply(4, 3) == 12
    assert calculator.multiply(-2, 3) == -6
    assert calculator.multiply(0, 5) == 0

def test_divide(calculator):
    assert calculator.divide(10, 2) == 5
    assert calculator.divide(-6, 2) == -3
    assert calculator.divide(5, 2) == 2.5

    with pytest.raises(ValueError, match="Cannot divide by zero"):
        calculator.divide(5, 0)

if __name__ == "__main__":
    pytest.main()
