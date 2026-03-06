import { useEffect, useState } from "react";
import StatsTable from "../components/StatsTable";
import { getUserMeasures, type MeasureSummary } from "../api/statsApi";

export default function StatsView() {
  const [measures, setMeasures] = useState<MeasureSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const userId = 1;

  useEffect(() => {
    getUserMeasures(userId)
      .then((data) => setMeasures(data.content))
      .catch((err) =>
        setError(err instanceof Error ? err.message : "Unexpected error")
      )
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="mx-auto flex max-w-5xl justify-center px-4 py-10">
        <div className="h-10 w-10 animate-spin rounded-full border-4 border-blue-600 border-t-transparent" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="mx-auto max-w-5xl px-4 py-8">
        <div className="rounded-2xl border-2 border-red-300 bg-red-50 px-5 py-4 text-lg font-semibold text-red-700 shadow">
          {error}
        </div>
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-5xl px-4 py-8">
      <StatsTable measures={measures} />
    </div>
  );
}