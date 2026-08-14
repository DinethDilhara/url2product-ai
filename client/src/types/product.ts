export type ExtractionStatus = "SUCCESS" | "PARTIAL" | "FAILED"

export type ProductDetailsResponse = {
  link: string
  title: string
  description: string
  currency: string
  price: number
  images: string[]
  status: ExtractionStatus
}

export type ApiResponse<T> = {
  success: boolean
  message: string
  data: T | null
  timestamp: string
}