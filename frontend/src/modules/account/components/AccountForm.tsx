import { useEffect, useState } from "react";

type AccountFormProps = {
  initialEmail: string;
  loading: boolean;
  onSubmit: (data: { email?: string; password?: string }) => void;
};

export default function AccountForm({
  initialEmail,
  loading,
  onSubmit,
}: AccountFormProps) {
  const [email, setEmail] = useState(initialEmail);
  const [password, setPassword] = useState("");

  useEffect(() => {
    setEmail(initialEmail);
  }, [initialEmail]);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const payload: { email?: string; password?: string } = {};

    if (email.trim() !== "" && email.trim() !== initialEmail.trim()) {
      payload.email = email.trim();
    }

    if (password.trim() !== "") {
      payload.password = password.trim();
    }

    onSubmit(payload);
  };

  const inputClassName =
    "w-full rounded-2xl border-2 border-slate-300 bg-white px-5 py-4 text-lg font-medium text-slate-800 placeholder:text-slate-400 outline-none transition focus:border-blue-600 focus:ring-2 focus:ring-blue-200";

  return (
    <div className="rounded-3xl border-4 border-slate-700 bg-white shadow-2xl">
      <div className="p-8 md:p-10">
        <h2 className="mb-8 text-4xl font-black text-slate-800">
          Account Settings
        </h2>

        <form onSubmit={handleSubmit} className="space-y-8">
          <div className="space-y-2">
            <label className="block text-sm font-semibold text-slate-700">
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className={inputClassName}
              placeholder="email@example.com"
            />
          </div>

          <div className="space-y-2">
            <label className="block text-sm font-semibold text-slate-700">
              New Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className={inputClassName}
              placeholder="Leave empty if unchanged"
            />
          </div>

          <div className="pt-4">
            <button
              type="submit"
              disabled={loading}
              className="flex w-full items-center justify-center rounded-2xl bg-slate-800 px-6 py-4 text-lg font-semibold text-white shadow-xl transition hover:bg-slate-900 disabled:cursor-not-allowed disabled:opacity-60"
            >
              {loading ? "Updating..." : "Update Account"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}