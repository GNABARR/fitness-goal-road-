import { Link } from "react-router-dom";

export default function AuthChoiceView() {
  return (
    <div className="mx-auto max-w-4xl px-4 py-12">
      <div className="rounded-3xl border-4 border-fuchsia-600 bg-white shadow-2xl">
        <div className="p-8 md:p-12">
          <h1 className="mb-4 text-center text-4xl font-black text-fuchsia-600">
            Welcome to Fitness Goal Roadmap
          </h1>

          <p className="mb-10 text-center text-lg font-medium text-slate-600">
            Choose how you want to continue 
          </p>

          <div className="grid gap-6 md:grid-cols-2">
            <Link
              to="/login"
              className="rounded-3xl border-2 border-blue-300 bg-blue-50 p-8 text-center shadow transition hover:-translate-y-1 hover:shadow-xl"
            >
              <h2 className="text-3xl font-black text-blue-600">Login</h2>
              <p className="mt-3 text-base font-medium text-slate-600">
                Access your existing account
              </p>
            </Link>

            <Link
              to="/register"
              className="rounded-3xl border-2 border-purple-300 bg-purple-50 p-8 text-center shadow transition hover:-translate-y-1 hover:shadow-xl"
            >
              <h2 className="text-3xl font-black text-purple-600">
                Create Account
              </h2>
              <p className="mt-3 text-base font-medium text-slate-600">
                Register
              </p>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}