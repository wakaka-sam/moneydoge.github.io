//获取应用实例
const app = getApp()

Page({
  data: {
    isSelected1: true,
    isSelected2: false
  },
  select1: function() {
    this.setData({
      isSelected1: true,
      isSelected2: false
    }) 
  },
  select2: function () {
    this.setData({
      isSelected1: false,
      isSelected2: true
    })
  }
})
