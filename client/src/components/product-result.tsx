import type { ProductDetailsResponse } from "@/types/product"
import { ProductImages } from "@/components/product-images"
import { ProductInfo } from "@/components/product-info"

type ProductResultProps = {
  product: ProductDetailsResponse
}

export function ProductResult({ product }: ProductResultProps) {
  return (
    <div className="mx-auto w-full max-w-5xl">
      <div className="grid gap-10 md:grid-cols-2">
        <ProductImages product={product} />
        <ProductInfo product={product} />
      </div>
    </div>
  )
}
