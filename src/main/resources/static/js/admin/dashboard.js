var addCategory = document.getElementById("add-category");
var addCategoryModal = document.getElementById("add-category-modal");
var closeAddCategoryModal = document.getElementById("close-add-category-modal")

var addProduct = document.getElementById("add-product");
var addProductModal = document.getElementById("add-product-modal");
var closeAddProductModal = document.getElementById("close-add-product-modal")

addCategory.onclick = function() {
    addCategoryModal.style.display = "block";
}

closeAddCategoryModal.onclick = function() {
    addCategoryModal.style.display = "none";
}

addProduct.onclick = function() {
    addProductModal.style.display = "block";
}

closeAddProductModal.onclick = function() {
    addProductModal.style.display = "none";
}