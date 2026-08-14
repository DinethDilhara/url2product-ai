import { useState } from "react"

import { Logo } from "@/components/logo"
import { ProductSearch } from "@/components/product-search"
import { ProductLoading } from "@/components/product-loading"
import { ProductEmpty } from "@/components/product-empty"
import { ProductResult } from "@/components/product-result"

import { scrapeProduct } from "@/services/productService"
import type { ProductDetailsResponse } from "@/types/product"

import { toast } from "@/components/ui/toast"

function App() {
  const [url, setUrl] = useState("")
  const [product, setProduct] = useState<ProductDetailsResponse | null>(null)
  const [loading, setLoading] = useState(false)

  const handleScrape = async () => {
    const trimmedUrl = url.trim()

    if (!trimmedUrl) {
      toast.add({
        title: "URL required",
        description: "Please enter a product URL.",
        type: "warning",
      })
      return
    }

    setLoading(true)
    setProduct(null)

    try {
      const result = await scrapeProduct(trimmedUrl)

      setProduct(result.data)

      toast.add({
        title: "Product scraped",
        description: result.message,
        type: "success",
      })
    } catch (error) {
      toast.add({
        title: "Scraping failed",
        description:
          error instanceof Error
            ? error.message
            : "Something went wrong while scraping the product.",
        type: "error",
      })
    } finally {
      setLoading(false)
    }
  }

  if (!product && !loading) {
    return (
      <main className="flex min-h-screen flex-col">
        <div className="flex flex-1 items-center justify-center px-4">
          <div className="flex w-full max-w-3xl -translate-y-12 flex-col items-center gap-8">
            <Logo />

            <p className="-mt-4 text-sm text-muted-foreground">
              Extract product information from any URL
            </p>

            <ProductSearch
              url={url}
              loading={loading}
              onUrlChange={setUrl}
              onScrape={handleScrape}
            />
          </div>
        </div>

        <footer className="pb-6 text-center text-xs text-muted-foreground">
          url2Product AI built with jsoup, spring AI and OpenAI. &copy;{" "}
          {new Date().getFullYear()} | Dineth Dilhara
        </footer>
      </main>
    )
  }

  return (
    <main className="min-h-screen">
      <header className="border-b">
        <div className="mx-auto flex max-w-6xl items-center gap-8 px-4 py-5">
          <div className="shrink-0">
            <Logo size="nav" />
          </div>

          <div className="flex flex-1 justify-center">
            <ProductSearch
              url={url}
              loading={loading}
              onUrlChange={setUrl}
              onScrape={handleScrape}
            />
          </div>
        </div>
      </header>

      <section className="px-4 py-10">
        {loading ? (
          <ProductLoading />
        ) : product ? (
          <ProductResult product={product} />
        ) : (
          <ProductEmpty />
        )}
      </section>
    </main>
  )
}

export default App
