import type { ProductDetailsResponse } from "@/types/product"

type ProductImagesProps = {
  product: ProductDetailsResponse
}

export function ProductImages({ product }: ProductImagesProps) {
  if (!product.images?.length) {
    return (
      <div className="flex aspect-square items-center justify-center rounded-xl border bg-muted/20">
        <p className="text-sm text-muted-foreground">
          No product image available
        </p>
      </div>
    )
  }

  return (
    <div className="space-y-4">
      <div className="overflow-hidden rounded-xl border bg-muted/20">
        <img
          src={product.images[0]}
          alt={product.title || "Product"}
          className="aspect-square w-full object-contain"
        />
      </div>

      {product.images.length > 1 && (
        <div className="grid grid-cols-4 gap-3">
          {product.images.slice(0, 4).map((image, index) => (
            <div
              key={`${image}-${index}`}
              className="overflow-hidden rounded-lg border bg-muted/20"
            >
              <img
                src={image}
                alt={`${product.title || "Product"} ${index + 1}`}
                className="aspect-square w-full object-contain"
              />
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
