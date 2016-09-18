
class Greeter
  attr_accessor :name
  @name
  def initialize(name)
    @name = name
  end

  def say_hello()
    puts "Hello #{@name}!"
  end
end

g = Greeter.new("World")
g.say_hello

puts Greeter.instance_methods false
puts g.respond_to?("say_hello")
puts g.respond_to?("name")
puts g.respond_to?("name=")

