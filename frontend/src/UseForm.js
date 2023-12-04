import {useState} from "react";

function useForm({additionalData}) {
    const [status, setStatus] = useState('');
    const [message, setMessage] = useState('');
    const [personalCode, setPersonalCode] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        setStatus('loading');
        setMessage('');

        const finalFormEndpoint = e.target.action;
        const data = Array.from(e.target.elements)
            .filter((input) => input.name)
            .reduce((obj, input) => Object.assign(obj, {[input.name]: input.value}), {});

        if (additionalData) {
            Object.assign(data, additionalData);
        }

        fetch(finalFormEndpoint, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then((response) => {
                if (response.status === 404) {
                    throw new Error("Not found customer");
                }
                return response.json();
            })
            .then((data) => {
                const {personalCode, message} = data;
                setPersonalCode(personalCode);
                setMessage(message);
                setStatus('success');
            })
            .catch((err) => {
                setMessage(err.message);
                setStatus('error');
            });
    };

    return {handleSubmit, status, message, personalCode};
}

export default useForm;