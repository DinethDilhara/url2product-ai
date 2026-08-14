import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

type ProductSearchProps = {
  url: string
  loading: boolean
  onUrlChange: (url: string) => void
  onScrape: () => void
}

export function ProductSearch({
  url,
  loading,
  onUrlChange,
  onScrape,
}: ProductSearchProps) {
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter" && !loading) {
      onScrape()
    }
  }

  return (
    <div className="w-full max-w-2xl">
      <div className="flex items-center gap-2 rounded-lg border bg-background p-1.5 shadow-sm transition-shadow focus-within:shadow-md">
        <Input
          value={url}
          onChange={(event) => onUrlChange(event.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Paste a product URL"
          disabled={loading}
          className="h-11 flex-1 border-0 bg-transparent px-4 text-sm shadow-none focus-visible:ring-0"
        />

        <Button
          onClick={onScrape}
          disabled={loading || !url.trim()}
          className="h-10 rounded-lg px-6"
        >
          {loading ? "Scraping..." : "Scrape"}
        </Button>
      </div>
    </div>
  )
}
