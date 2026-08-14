import type {
  ApiResponse,
  ProductDetailsResponse,
} from "@/types/product"

const API_BASE_URL = "http://localhost:8080"

export async function scrapeProduct(
  url: string
): Promise<ApiResponse<ProductDetailsResponse>> {
  const response = await fetch(
    `${API_BASE_URL}/api/v1/product-request/extract`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ url }),
    }
  )

  let result: ApiResponse<ProductDetailsResponse>

  try {
    result = await response.json()
  } catch {
    throw new Error("Invalid response received from the server.")
  }

  if (!response.ok || !result.success) {
    throw new Error(result.message || "Failed to scrape product.")
  }

  if (!result.data) {
    throw new Error("No product data returned from the server.")
  }

  return result
}