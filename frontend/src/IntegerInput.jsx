// IntegerInput.jsx
import React, {useState} from 'react';

const isValid = (value, min, max) =>
    value !== '' &&
    value !== '-' &&
    (min === undefined || value >= min) &&
    (max === undefined || value <= max);

export const IntegerInput = ({value, min, max, onChange, placeholder, name, className}) => {
    const regexp = new RegExp(`^-?[0-9]*$`);
    const [internalValue, setInternalValue] = useState(value);
    return (
        <input type="text"
               placeholder={placeholder}
               className={className}
               value={internalValue}
               name={name}
               onChange={(event) => {
                   const newValue = event.target.value;
                   if (regexp.test(newValue)) {
                       setInternalValue(newValue);
                       let newValid = isValid(newValue, min, max);
                       if (newValid) {
                           onChange(newValue);
                       }
                   }
               }}
               onBlur={() => {
                   if (internalValue < min) {
                       setInternalValue(min);
                   } else if (internalValue > max) {
                       setInternalValue(max);
                   } else {
                       setInternalValue(value);
                   }
               }}
        />
    );
};