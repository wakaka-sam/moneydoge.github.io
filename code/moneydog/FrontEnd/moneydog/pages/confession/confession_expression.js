// pages/confession/confession_expression.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    content:"",
    sessionID: "847694c4-14dd-47b2-8922-facd8e379f47",/*注意与用户有关的交互都需要sessionID,初始化为永久sessionId*/
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    const session_id = wx.getStorageSync('SessionId');
    if (session_id != null) {
      that.setData({ sessionID: session_id })
    }
    that.setData({
      content:""
    })

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

  },
  //获取表白墙发布的内容
  confession_content: function (e) {
    this.setData({
      content: e.detail.value
    })
  },
  gotoupload5:function(){
    var that = this;
    console.log(that.data.content);
    wx.request({
      url: "https://moneydog.club:3336/LoveWall/createWall",
      header: { sessionId: that.data.sessionID.toString(), "Content-Type": "application/x-www-form-urlencoded" },//请求时要加上sessionID
      method: "POST",
      data: {
        detail:that.data.content,
      },

      success: function (res) {
        wx.navigateBack({
          delta: 1  //小程序关闭当前页面返回上一页面
        })
        wx.showToast({
          title: '表白发布成功',
          icon: 'success',
          duration: 2000
        })
      },
    })
  }
})