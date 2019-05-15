

Page({
  data: {
    money:100
  },
  add_money:function(){
    this.setData({
      money:this.data.money + 100
    }
    )
  },
  sub_money: function () {
    if (this.data.money > 0)
    this.setData({
      money: this.data.money - 100
    }
    )
  },
  
})
