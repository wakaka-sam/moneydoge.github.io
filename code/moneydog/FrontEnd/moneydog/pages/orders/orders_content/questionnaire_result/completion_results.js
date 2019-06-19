// pages/orders/orders_content/completion_results.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fillArray: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if(options.fill) {
      console.log(JSON.parse(options.fill))
      var fillString = JSON.parse(options.fill)
      var j = 0
      for(var i = 0;i < fillString.length;i++){
        //console.log(fillString[i])
        if(fillString[i] == ';') {
          this.data.fillArray.push(fillString.substring(j,i))
          j = i + 1
        }
      }
      this.setData({
        fillArray: this.data.fillArray
      })
      console.log(this.data.fillArray)
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})