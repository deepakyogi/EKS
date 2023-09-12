###########################
## Azure Provider - Main ##
###########################

# Define Terraform provider
terraform {
  required_version = ">= 0.15"
}

terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = ">=2.50.0"
    }
  }
}

# Configure the Azure provider
provider "azurerm" {
  # skip_provider_registration = "true"
  features {}
  environment     = "public"
  subscription_id = "31e6d5a5-e531-466c-ba76-4b44470deb25"
  client_id       = "8b6229ee-c4b1-463c-9189-1289c9f43288"
  client_secret   = ".Yp8Q~jlWEZoAiGYKFdKMsq-nxBQ5BlL8kACwcGi"
  tenant_id       = "ad0ebff6-9222-4151-ad61-6d2739e1c19c"
}

# State Backend
terraform {
  backend "azurerm" {
    resource_group_name   = "gitlab"
    storage_account_name  = "gitlabsa1507"
    container_name        = "terraform-state"
    key                   = "prod.terraform.tfstate"
  }
}
