import { Separator } from "@/components/ui/separator"
import type { ProductDetailsResponse } from "@/types/product"

type ProductInfoProps = {
  product: ProductDetailsResponse
}

export function ProductInfo({ product }: ProductInfoProps) {
  const statusClass =
    product.status === "SUCCESS"
      ? "bg-green-100 text-green-700 dark:bg-green-950 dark:text-green-400"
      : product.status === "PARTIAL"
        ? "bg-yellow-100 text-yellow-700 dark:bg-yellow-950 dark:text-yellow-400"
        : "bg-red-100 text-red-700 dark:bg-red-950 dark:text-red-400"

  return (
    <div className="flex flex-col">
      <div className="flex items-start justify-between gap-4">
        <div className="space-y-2">
          <h1 className="text-2xl font-semibold tracking-tight">
            {product.title || "Untitled product"}
          </h1>

          <p className="text-sm text-muted-foreground">
            {product.description || "No description available."}
          </p>
        </div>

        <span
          className={`shrink-0 rounded-full px-3 py-1 text-xs font-medium ${statusClass}`}
        >
          {product.status}
        </span>
      </div>

      <Separator className="my-6" />

      <div>
        <p className="text-sm text-muted-foreground">Price</p>

        {product.price != null ? (
          <p className="mt-1 text-3xl font-semibold tracking-tight">
            {product.currency}{" "}
            {product.price.toLocaleString(undefined, {
              minimumFractionDigits: 2,
              maximumFractionDigits: 2,
            })}
          </p>
        ) : (
          <p className="mt-1 text-sm text-muted-foreground">
            Price unavailable
          </p>
        )}
      </div>

      <Separator className="my-6" />

      <div className="space-y-4 text-sm">
        <div>
          <p className="text-muted-foreground">Currency</p>
          <p className="mt-1 font-medium">
            {product.currency || "Not available"}
          </p>
        </div>

        <Separator />

        <div>
          <p className="text-muted-foreground">Extraction status</p>

          <p className="mt-1 font-medium">{product.status}</p>
        </div>

        <Separator />

        <div>
          <p className="text-muted-foreground">Source</p>

          <a
            href={product.link}
            target="_blank"
            rel="noopener noreferrer"
            className="mt-1 block truncate text-blue-600 hover:underline"
          >
            {product.link}
          </a>
        </div>
      </div>
    </div>
  )
}
