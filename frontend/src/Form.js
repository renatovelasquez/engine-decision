import useForm from "./UseForm";

const FORM_ENDPOINT = "http://localhost:8080/api/engine";

const Form = () => {
    const additionalData = {
        sent: new Date().toISOString(),
    };

    const {handleSubmit, status, message, personalCode} = useForm({
        additionalData,
    });

    if (status === "success") {
        return (
            <>
                <div className="text-2xl">{personalCode}</div>
                <div className="text-md">{message}</div>
                <div className="pt-0 mb-3">
                    <button
                        className="active:bg-blue-600 hover:shadow-lg focus:outline-none px-6 py-3 mb-1 mr-1 text-sm font-bold text-white uppercase transition-all duration-150 ease-linear bg-blue-500 rounded shadow outline-none"
                        onClick={() => window.location.reload()}
                        type="button"
                    >
                        Back
                    </button>
                </div>
            </>
        );
    }

    if (status === "error") {
        return (
            <>
                <div className="text-2xl">Something happened</div>
                <div className="text-md">{message}</div>
                <div className="pt-0 mb-3">
                    <button
                        className="active:bg-blue-600 hover:shadow-lg focus:outline-none px-6 py-3 mb-1 mr-1 text-sm font-bold text-white uppercase transition-all duration-150 ease-linear bg-blue-500 rounded shadow outline-none"
                        onClick={() => window.location.reload()}
                        type="button"
                    >
                        Back
                    </button>
                </div>
            </>
        );
    }

    return (
        <form
            action={FORM_ENDPOINT}
            onSubmit={handleSubmit}
            method="POST"
        >
            <div className="pt-0 mb-3">
                <input
                    type="text"
                    placeholder="Personal code"
                    name="personalCode"
                    className="focus:outline-none focus:ring relative w-full px-3 py-3 text-sm text-gray-600 placeholder-gray-400 bg-white border-0 rounded shadow outline-none"
                    required
                />
            </div>
            <div className="pt-0 mb-3">
                <input
                    type="text"
                    placeholder="Loan amount"
                    name="loanAmount"
                    className="focus:outline-none focus:ring relative w-full px-3 py-3 text-sm text-gray-600 placeholder-gray-400 bg-white border-0 rounded shadow outline-none"
                    required
                />
            </div>
            <div className="pt-0 mb-3">
                <input
                    type="text"
                    placeholder="Loan period (months)"
                    name="loanPeriod"
                    className="focus:outline-none focus:ring relative w-full px-3 py-3 text-sm text-gray-600 placeholder-gray-400 bg-white border-0 rounded shadow outline-none"
                    required
                />
            </div>

            {status !== "loading" && (
                <div className="pt-0 mb-3">
                    <button
                        className="active:bg-blue-600 hover:shadow-lg focus:outline-none px-6 py-3 mb-1 mr-1 text-sm font-bold text-white uppercase transition-all duration-150 ease-linear bg-blue-500 rounded shadow outline-none"
                        type="submit"
                    >
                        Send
                    </button>
                </div>
            )}
        </form>
    );
};

export default Form;